package com.danger.insurance.news.models.dto.mappers;

import com.danger.insurance.news.data.entities.NewsEntity;
import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.NewsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T18:12:44+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsEntity toEntity(NewsDTO source) {
        if ( source == null ) {
            return null;
        }

        NewsEntity newsEntity = new NewsEntity();

        newsEntity.setNewsId( source.getNewsId() );
        newsEntity.setTitle( source.getTitle() );
        newsEntity.setDescription( source.getDescription() );
        newsEntity.setContent( source.getContent() );
        newsEntity.setPictureUrl( source.getPictureUrl() );
        newsEntity.setPostDate( source.getPostDate() );

        return newsEntity;
    }

    @Override
    public NewsEntity newsCreateToEntity(NewsDTO source) {
        if ( source == null ) {
            return null;
        }

        NewsEntity newsEntity = new NewsEntity();

        newsEntity.setNewsId( source.getNewsId() );
        newsEntity.setTitle( source.getTitle() );
        newsEntity.setDescription( source.getDescription() );
        newsEntity.setContent( source.getContent() );
        newsEntity.setPictureUrl( source.getPictureUrl() );
        newsEntity.setPostDate( source.getPostDate() );

        return newsEntity;
    }

    @Override
    public NewsDTO mergeToNewsDTO(NewsDTO newsDTO, NewsCreateDTO newsCreateDTO) {
        if ( newsCreateDTO == null ) {
            return newsDTO;
        }

        newsDTO.setTitle( newsCreateDTO.getTitle() );
        newsDTO.setDescription( newsCreateDTO.getDescription() );
        newsDTO.setContent( newsCreateDTO.getContent() );

        return newsDTO;
    }

    @Override
    public NewsDTO toDTO(NewsEntity source) {
        if ( source == null ) {
            return null;
        }

        NewsDTO newsDTO = new NewsDTO();

        newsDTO.setNewsId( source.getNewsId() );
        newsDTO.setTitle( source.getTitle() );
        newsDTO.setDescription( source.getDescription() );
        newsDTO.setContent( source.getContent() );
        newsDTO.setPictureUrl( source.getPictureUrl() );
        newsDTO.setPostDate( source.getPostDate() );

        return newsDTO;
    }

    @Override
    public NewsCreateDTO mergeToNewsCreateDTO(NewsCreateDTO newsCreateDTO, NewsDTO newsDTO) {
        if ( newsDTO == null ) {
            return newsCreateDTO;
        }

        newsCreateDTO.setTitle( newsDTO.getTitle() );
        newsCreateDTO.setDescription( newsDTO.getDescription() );
        newsCreateDTO.setContent( newsDTO.getContent() );

        return newsCreateDTO;
    }

    @Override
    public void updateNewsDTO(NewsDTO source, NewsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setNewsId( source.getNewsId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setContent( source.getContent() );
        target.setPictureUrl( source.getPictureUrl() );
        target.setPostDate( source.getPostDate() );
    }

    @Override
    public void updateNewsEntity(NewsDTO source, NewsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setNewsId( source.getNewsId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setContent( source.getContent() );
        target.setPictureUrl( source.getPictureUrl() );
        target.setPostDate( source.getPostDate() );
    }
}
