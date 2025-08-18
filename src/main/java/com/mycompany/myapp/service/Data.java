package com.mycompany.myapp.service;

public class Data {

    private Board board;
    private Card card;
    private Boolean voted;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Boolean getVoted() {
        return voted;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;
    }

    public class Board {

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Card {

        private Long idShort;
        private String name;
        private String id;

        public Long getIdShort() {
            return idShort;
        }

        public void setIdShort(Long idShort) {
            this.idShort = idShort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
