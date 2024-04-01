package ch1;

public interface Tv {

    // 인터페이스
    // 완전 추상화
    // 추상 메소드 - public
    // 객체 생성 불가
    // 스프링 프레임 워크는 틀(interface)을 다 만들어 놓은 상태

    void PowerOn();

    void Poweroff();

    void volumeUp();

    void volumeDown();

}
