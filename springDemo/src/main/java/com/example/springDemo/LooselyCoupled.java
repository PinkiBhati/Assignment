package com.example.springDemo;

interface Travel {
    void go();
}

 class Car implements Travel {
    public void go()
    {
        System.out.println("Travelling by Car...");
    }
}


 class Bus implements Travel {
    public void go()
    {
        System.out.println("Travelling by Bus..");
    }
}


//Journey class enables loose coupling as we are only having reference to the Travel interface.
 class Journey {
    Travel travel;
    public Travel getTravel() {
        return this.travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public void startJourney()
    {
        travel.go();
    }

}


//our main class

public class LooselyCoupled {

    public static void main(String[] args) {
        Journey journey=new Journey();
        Car car=new Car();
        Bus bus=new Bus();
        journey.setTravel(bus);
        journey.startJourney();
        journey.setTravel(car);
        journey.startJourney();
    }
}
