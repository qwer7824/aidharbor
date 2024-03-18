package com.aidharbor.Service;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.ProductCategory;
import com.aidharbor.Entity.Product;
import com.aidharbor.Repository.CategoryRepository;
import com.aidharbor.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImgService imgService;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;


    // 상품 저장
    @Transactional
    public void productSave(ProductDTO productDTO, MultipartFile titleImg) throws IOException {
        ProductCategory productCategory = categoryRepository.findById(productDTO.getProductCategory().getId()).orElse(null);
        String storedFileName = s3Uploader.upload(titleImg, "images");
        String content = imgService.convert(productDTO);

        Product product = Product.builder()
                .title(productDTO.getTitle())
                .subTitle(productDTO.getSubTitle())
                .content(content)
                .titleImgUrl(storedFileName)
                .productCategory(productCategory)
                .build();

        productRepository.save(product);
    }

    // 상품 리스트 (카테고리)
    @Transactional(readOnly = true)
    public List<ProductDTO> productList(int categoryId) {

        if(categoryId == 0){
            List<Product> productList = productRepository.findAll();
            return productListToDTOList(productList);
        }
        List<Product> product = productRepository.findByProductCategoryId(categoryId);
        return productListToDTOList(product);
    }

    // 상품 상세
    @Transactional(readOnly = true)
    public ProductDTO getProductDetail(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);

        ProductDTO productDTO = ProductDTO.of(product);

        return productDTO;
    }

    // 상품 수정
    @Transactional
    public Long productUpdate(ProductDTO productDTO,MultipartFile titleImg) throws IOException {

        Product product = productRepository.findById(productDTO.getId()).orElseThrow(null);

        if (!titleImg.isEmpty()) {
            String url = imgService.imgSubString(product.getTitleImgUrl());
            s3Uploader.deleteFile(url);
            String storedFileName = s3Uploader.upload(titleImg, "images");
            product.updateTitleImgUrl(productDTO,storedFileName);
        } else {
            product.updateProduct(productDTO);
        }
        productRepository.save(product);
        return product.getId();
    }


    // 상품 삭제
    public void productDelete(long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(null);
        productRepository.delete(product);
        imgService.imgProductDelete(product);
    }

    public List<ProductDTO> productListToDTOList(List<Product> productList) {
        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
