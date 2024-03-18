package com.aidharbor.Service;


import com.aidharbor.DTO.Category.ProductCategoryCreateRequest;
import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.DTO.Product.ProductResponseDTO;
import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
import com.aidharbor.Excption.CategoryNotFoundException;
import com.aidharbor.Repository.CategoryRepository;
import com.aidharbor.Repository.MemberRepository;
import com.aidharbor.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;
    private final ImgService imgService;

    @Transactional(readOnly = true)
    public List<ProductCategoryDto> findAll() {
        List<ProductCategory> categories = categoryRepository.findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
        return ProductCategoryDto.toDtoList(categories);
    }

    @Transactional(readOnly = true)
    public ProductCategoryCreateRequest categoryDetail(int categoryId) {
        ProductCategory categories = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        ProductCategoryCreateRequest productCategoryCreateRequest = ProductCategoryCreateRequest.of(categories);

        return productCategoryCreateRequest;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductCategoryList(int categoryId) {
        List<Product> productList;

        if (categoryId == 0) {
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByProductCategoryId(categoryId);
        }

        return productList.stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getTitle(), p.getTitleImgUrl()))
                .collect(Collectors.toList());
    }



    @Transactional
    public void create(ProductCategoryCreateRequest req, MultipartFile categoryImg) throws IOException {
        String storedFileName = s3Uploader.upload(categoryImg, "images");

        ProductCategory parent = Optional.ofNullable(req.getParentId())
                .map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .orElse(null);

        ProductCategory productCategory = ProductCategory.builder()
                .name(req.getName())
                .parent(parent)
                .categoryImg(storedFileName)
                .build();

        categoryRepository.save(productCategory);
    }

    @Transactional
    public void delete(int id) throws IOException {
        ProductCategory productCategory = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        imgService.imgCategoryDelete(productCategory);
        categoryRepository.delete(productCategory);
    }

    @Transactional
    public void categoryUpdate(ProductCategoryCreateRequest req,MultipartFile categoryImg) throws IOException {
    ProductCategory productCategory = categoryRepository.findById(req.getId()).orElseThrow();


        if (!categoryImg.isEmpty()) {
            String url = imgService.imgSubString(req.getCategoryImg());
            s3Uploader.deleteFile(url);
            String storedFileName = s3Uploader.upload(categoryImg, "images");
            productCategory.updateImgUrl(req,storedFileName);
        } else {
            productCategory.CategoryUpdate(req);
        }

    categoryRepository.save(productCategory);
    }

    public List<ProductCategoryDto> getChildCategories(int categoryId) {
        // 먼저 주어진 ID로 부모 카테고리를 찾습니다.
        ProductCategory parentProductCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category Id: " + categoryId));

        // 이후 모든 카테고리를 가져와 CategoryHelper를 이용하여 카테고리 트리를 만듭니다.
        List<ProductCategory> allCategories = categoryRepository.findAll();
        List<ProductCategoryDto> allProductCategoryDtos = ProductCategoryDto.toDtoList(allCategories);

        // 전체 카테고리 트리에서 해당 카테고리의 하위 카테고리들을 찾아 반환합니다.
        return allProductCategoryDtos.stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(new ProductCategoryDto())
                .getChildren();
    }

}