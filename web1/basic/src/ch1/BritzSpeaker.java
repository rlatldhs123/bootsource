package ch1;

public class BritzSpeaker implements Speaker {

    public BritzSpeaker() {
        System.out.println("스피커 생성");
    }

    @Override
    public void volumeUp() {

        System.out.println("스피커 볼륨 업");

    }

    @Override
    public void volumeDown() {
        System.out.println("스피커 볼륨 다운");

    }

}
