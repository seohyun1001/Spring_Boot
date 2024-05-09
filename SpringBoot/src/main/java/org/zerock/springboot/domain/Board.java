package org.zerock.springboot.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;


    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "board",
            cascade = {CascadeType.ALL}, // Board 엔티티 객체의 모든 상태 변화에 BoardImage 객체들도 같이 변경됨
            fetch = FetchType.LAZY,
            orphanRemoval = true) // -> BoardImage의 board변수
    @Builder.Default
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){

        BoardImage boardImage = BoardImage.builder().
                uuid(uuid).
                fileName(fileName).
                board(this).
                ord(imageSet.size()) // 이미지가 추가될 때마다 1씩 늘어남
                .build();
        imageSet.add(boardImage);

        }

    public void clearImages() {

        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();

    }

}
