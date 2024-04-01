package ch1;

public class SamsungTv implements Tv {

    @Override
    public void PowerOn() {
        System.out.println("SamsungTv 전원 on");

    }

    @Override
    public void Poweroff() {
        System.out.println("SamsungTv 전원 off");

    }

    @Override
    public void volumeUp() {
        System.out.println("SamsungTv 전원 up");

    }

    @Override
    public void volumeDown() {
        System.out.println("SamsungTv 전원 down");

    }

}
