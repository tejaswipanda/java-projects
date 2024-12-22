package intro

import (
	"fmt"
	"sync"
	"time"
)

func Welcome(conferenceName string) {
	fmt.Println("Welcome to", conferenceName, "our booking application")
	fmt.Println("Get your tickets here to attend")
}

func CreateMapAndPrint() {
	var myMap = make(map[string]string)
	myMap["1st Key"] = "1st value"
	fmt.Printf("myMap: %v\n", myMap)
	var myMapList = make([]map[string]string, 0) //List of map and initial size
	fmt.Printf("myMapList: %v\n", myMapList)
}

type UserData struct {
	IsValidUserName bool
	Username        string
	UserTickets     int
}

func PrintStructureAndReturn() UserData {
	var myMapList = make([]UserData, 0) //List of map and initial size
	myMapList = append(myMapList, UserData{Username: "Hari", UserTickets: 100})
	for _, element := range myMapList {

		fmt.Println(element)
	}
	return myMapList[0]
}
func SendTickets(userTickets int, userName string) {
	time.Sleep(10 * time.Second)
	fmt.Println(userTickets, "Tickets booked for User", userName)
	fmt.Println("####################")
	fmt.Println("Sending Tickets")
	fmt.Println("####################")
}

func AsyncFunction(wg *sync.WaitGroup) {
	for i := 0; i < 10; i++ {
		fmt.Println("Async Call is happening")
	}

	defer wg.Done()
}
