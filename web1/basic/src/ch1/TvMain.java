package ch1;

public class TvMain {
    public static void main(String[] args) {

        // tv 객체 생성

        LgTv tv = new LgTv();
        // Tv tv = new LgTv();

        // SamsungTv samsungTv = new SamsungTv();

        // 왼쪽은 부모 오른쪽은 자식의 개념으로 만들기
        // 코드 수정이 편리함 [오른족만 바꾸면 되기에]

        // 오버라이딩 시 자식 실행

        tv.setSpeaker(new BritzSpeaker());

        tv.PowerOn();
        tv.volumeUp();
        tv.volumeDown();
        tv.Poweroff();

    }

}
