package controller;

public class Request {
    RequestType requestType;
    int floor;

    public Request(RequestType requestType, int floor) {
        this.requestType = requestType;
        this.floor = floor;
    }
}

