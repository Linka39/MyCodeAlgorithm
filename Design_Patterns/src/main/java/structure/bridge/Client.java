package structure.bridge;

import structure.bridge.engine.HybridEngine;

public class Client {
    public static void main(String[] args) {
        RefinedCar car = new BossCar(new HybridEngine());
        car.drive();
    }
}
