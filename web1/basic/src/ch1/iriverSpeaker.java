package ch1;

public class iriverSpeaker implements Speaker {

    public iriverSpeaker() {
        System.out.println("아이리버 스피커 생성");
    }

    @Override
    public void volumeUp() {

        System.out.println("아이리버 스피커 볼륨 업");

    }

    @Override
    public void volumeDown() {
        System.out.println("아이리버 스피커 볼륨 다운");

    }

}
