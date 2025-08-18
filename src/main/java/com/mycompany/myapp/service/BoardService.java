package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.mapper.BoardMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Board}.
 */
@Service
@Transactional
public class BoardService {

    private static final Logger LOG = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;

    private final BoardMapper boardMapper;

    private final TrelloService trelloService;

    public BoardService(BoardRepository boardRepository, BoardMapper boardMapper, TrelloService trelloService) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.trelloService = trelloService;
    }

    /**
     * Save a board.
     *
     * @param boardDTO the entity to save.
     * @return the persisted entity.
     */
    public BoardDTO save(BoardDTO boardDTO) {
        LOG.debug("Request to save Board : {}", boardDTO);

        Board board = boardMapper.toEntity(boardDTO);
        board.setCreatedAt(Instant.now());
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    /**
     * Update a board.
     *
     * @param boardDTO the entity to save.
     * @return the persisted entity.
     */
    public BoardDTO update(BoardDTO boardDTO) {
        LOG.debug("Request to update Board : {}", boardDTO);
        Board board = boardMapper.toEntity(boardDTO);
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    /**
     * Partially update a board.
     *
     * @param boardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BoardDTO> partialUpdate(BoardDTO boardDTO) {
        LOG.debug("Request to partially update Board : {}", boardDTO);

        return boardRepository
            .findById(boardDTO.getId())
            .map(existingBoard -> {
                boardMapper.partialUpdate(existingBoard, boardDTO);

                return existingBoard;
            })
            .map(boardRepository::save)
            .map(boardMapper::toDto);
    }

    /**
     * Get all the boards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BoardDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Boards");
        return boardRepository.findAll(pageable).map(boardMapper::toDto);
    }

    /**
     * Get one board by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BoardDTO> findOne(Long id) {
        LOG.debug("Request to get Board : {}", id);
        Optional<Board> board = boardRepository.findById(id);
        return board.map(boardMapper::toDto);
    }

    /**
     * Delete the board by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Board : {}", id);
        boardRepository.deleteById(id);
    }
}
