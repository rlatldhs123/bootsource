package ch1;

public class LgTv implements Tv {
    // 멤버 변수 초기화
    // 기본형 타입 : int lomg boolean은 초기값이 좀 달랐다
    // 1) 정수 타입 : 0
    // 2) 불린 타입 :false 0.0
    // 3) 실수 타입 0.0

    // 참조형 타입(참조형, 배열 대문자로 시작되는 애들) : null 로 초기화
    private Speaker speaker; // private BritzSpeaker britzSpeaker=null;

    // 멤버 변수 초기화 방법

    // 1) setter 메소드 이용
    // 2) 생성자 이용

    // 스피커를 구현하는 부모 클래스로 만들면 어떤게 들어오든 다 받아 들일 수 있다 (다형성)

    public LgTv() {
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public LgTv(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void PowerOn() {
        System.out.println("엘지티비 전원 on");

    }

    @Override
    public void Poweroff() {
        System.out.println("엘지티비 전원 off");

    }

    @Override
    public void volumeUp() {
        // System.out.println("엘지티비 볼륨 up");
        speaker.volumeUp();

    }

    @Override
    public void volumeDown() {
        // System.out.println("엘지티비 볼륨 down");
        speaker.volumeDown();

    }

}
