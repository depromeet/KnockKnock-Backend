package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.CategoryRepository;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateCategoryRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CategoryDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CategoryListResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryListResponse findAllCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream()
            .filter(category -> !category.getId().equals(Category.defaultEmptyCategoryId))
            .map(CategoryDto::new)
            .collect(Collectors.toList());
        return new CategoryListResponse(categoryDtoList);
    }

    public CategoryDto saveCategory(CreateCategoryRequest createCategoryRequest){

        Category category = Category.builder()
            .emoji(createCategoryRequest.getEmoji())
            .content(createCategoryRequest.getContent())
            .build();

        categoryRepository.save(category);

        return new CategoryDto(category);
    }

    @Cacheable(cacheNames = "famousCategory" , cacheManager = "redisCacheManager")
    public CategoryListResponse findFamousCategory(){
        List<CategoryDto> categoryDtos = categoryRepository.findFamousCategory().stream()
            .map(CategoryDto::new)
            .collect(Collectors.toList());
        return new CategoryListResponse(categoryDtos);
    }
}
