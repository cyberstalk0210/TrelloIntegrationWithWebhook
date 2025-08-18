package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.service.dto.BoardListDTO;
import com.mycompany.myapp.service.mapper.BoardListMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.BoardList}.
 */
@Service
@Transactional
public class BoardListService {

    private static final Logger LOG = LoggerFactory.getLogger(BoardListService.class);

    private final BoardListRepository boardListRepository;

    private final RestTemplate restTemplate;

    private final BoardListMapper boardListMapper;

    public BoardListService(BoardListRepository boardListRepository, RestTemplate restTemplate, BoardListMapper boardListMapper) {
        this.boardListRepository = boardListRepository;
        this.restTemplate = restTemplate;
        this.boardListMapper = boardListMapper;
    }

    /**
     * Save a boardList.
     *
     * @param boardListDTO the entity to save.
     * @return the persisted entity.
     */
    public BoardListDTO save(BoardListDTO boardListDTO) {
        LOG.debug("Request to save BoardList : {}", boardListDTO);
        BoardList boardList = boardListMapper.toEntity(boardListDTO);

        boardList = boardListRepository.save(boardList);
        return boardListMapper.toDto(boardList);
    }

    /**
     * Update a boardList.
     *
     * @param boardListDTO the entity to save.
     * @return the persisted entity.
     */
    public BoardListDTO update(BoardListDTO boardListDTO) {
        LOG.debug("Request to update BoardList : {}", boardListDTO);
        BoardList boardList = boardListMapper.toEntity(boardListDTO);
        boardList = boardListRepository.save(boardList);
        return boardListMapper.toDto(boardList);
    }

    /**
     * Partially update a boardList.
     *
     * @param boardListDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BoardListDTO> partialUpdate(BoardListDTO boardListDTO) {
        LOG.debug("Request to partially update BoardList : {}", boardListDTO);

        return boardListRepository
            .findById(boardListDTO.getId())
            .map(existingBoardList -> {
                boardListMapper.partialUpdate(existingBoardList, boardListDTO);

                return existingBoardList;
            })
            .map(boardListRepository::save)
            .map(boardListMapper::toDto);
    }

    /**
     * Get all the boardLists.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BoardListDTO> findAll() {
        LOG.debug("Request to get all BoardLists");
        return boardListRepository.findAll().stream().map(boardListMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one boardList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BoardListDTO> findOne(Long id) {
        LOG.debug("Request to get BoardList : {}", id);
        return boardListRepository.findById(id).map(boardListMapper::toDto);
    }

    /**
     * Delete the boardList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete BoardList : {}", id);
        boardListRepository.deleteById(id);
    }
}
