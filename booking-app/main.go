package main

import (
	"booking-app/intro"
	"fmt"
	"sync"
	"time"
)

var wg = sync.WaitGroup{}

var remainingTickets = 50 //(this is called package level variable, available to whole package)
func main() {
	var p *int
	x := 42
	p = &x
	*p = 45
	fmt.Print(*p, x) //pointers, can store the memory address of another variable.
	/* adding 1 thread to this so that main function will consider this as these is no loop here.
	If there won't be any loop main function won't wait for any go keyword*/
	//var wg sync.WaitGroup
	startNow := time.Now()
	wg.Add(1)
	go intro.AsyncFunction(&wg) //Goroutines are lightweight threads managed by the Go runtime. They are incredibly easy to use.
	wg.Wait()                   // wait untill all thread gets executed

	/*Channels provide a way for goroutines to communicate and synchronize with each other.
	  They can be thought of as typed conduits through which you can send and receive values*/
	var bookingsSlice []string
	for /*we can add condition here too (boolean)*/ {
		conferenceName := "go conference"
		var isvalidUsername bool = false
		var username string
		var userTickets int
		intro.Welcome(conferenceName)
		//intro.CreateMapAndPrint()
		userdata := getUserDetails(isvalidUsername, username, userTickets)

		if !userdata.IsValidUserName {
			break
		}
		fmt.Println("User", userdata.Username, "requested to book", userdata.UserTickets, "tickets")

		//slice is abstraction of array with dynamic size
		if remainingTickets < 0 {
			fmt.Println("We don't have enough tickets!!! Sorry!!!")
			fmt.Println("Remaining Tickets", remainingTickets+userdata.UserTickets)
			break
		}

		bookingsSlice = append(bookingsSlice, userdata.Username)
		fmt.Printf("The Whole Slice : %v\n", bookingsSlice)
		fmt.Printf("The length of Slice : %v\n", len(bookingsSlice))
		fmt.Printf("Remaining Ticket : %v\n", remainingTickets)

		go intro.SendTickets(userdata.UserTickets, userdata.Username)
		//newarray := []string{"A", "B", "C", "D", "E"}
		//newarray2 := []string{}
		/*for index, element := range newarray {
			fmt.Println("index", index, "element", element)
			fmt.Println(strings.Fields("Hii Teja")) //separates spaces

		}*/

	}
	//if we don't want to use index we can replace with _ so make go understand to ignore
	fmt.Println("Time taken : ", time.Since(startNow))
}

func getUserDetails(isvalidUsername bool, username string, userTickets int) intro.UserData {

	//uint is  for +ve number
	fmt.Println("Enter User Name")
	fmt.Scan(&username)

	isvalidUsername = len(username) > 2 //operator

	if isvalidUsername {
		fmt.Println("Enter the number of tickets")
		fmt.Scan(&userTickets)
	} else {
		fmt.Println("Sorry! Wrong user input")
	}
	remainingTickets = remainingTickets - userTickets
	return intro.UserData{IsValidUserName: isvalidUsername, Username: username, UserTickets: userTickets}
}
