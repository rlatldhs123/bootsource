package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service // 서비스 클래스 위에 쓰는 어노테이션 / 스프링 컨테이너가 해당 클래스의 객체를 생성한 후 관리 해줌
@RequiredArgsConstructor // not null , final 이 붙은 멤버 변수를 대상으로 생성자 생성
public class MemoServiceImpl {

    private final MemoRepository memoRepository;

    public List<MemoDto> getMemoList() {

        List<Memo> entites = memoRepository.findAll();
        List<MemoDto> list = new ArrayList<>();
        entites.forEach(entite -> list.add(entityToDto(entite)));

        return list;
    }

    private MemoDto entityToDto(Memo entity) {
        MemoDto mDto = MemoDto.builder()
                .mno(entity.getMno())
                .memoText(entity.getMemoText())
                .build();
        return mDto;

    }

    public MemoDto getMemo(Long mno) {

        Memo entity = memoRepository.findById(mno).get();

        return entityToDto(entity);
    }

    public MemoDto updateMemo(MemoDto mDto) {

        Memo entity = memoRepository.findById(mDto.getMno()).get();

        // 변경
        entity.setMemoText(mDto.getMemoText());

        return entityToDto(memoRepository.save(entity));
    }

    public void deleteMemo(Long mno) {

        // Memo entity = memoRepository.findById(mno).get();

        memoRepository.deleteById(mno);

    }

    // create

    public void insertMemo(MemoDto mDto) {

        // dto 바구니에 있던걸 entity 로 바꿔준다
        memoRepository.save(dtoToEntity(mDto));

    }

    private Memo dtoToEntity(MemoDto mDto) {
        Memo entity = Memo.builder()
                .mno(mDto.getMno())
                .memoText(mDto.getMemoText())
                .build();
        return entity;
    }

}
