package main

import (
	"encoding/json"
	"net/http"

	"github.com/gorilla/mux"
)

type User struct {
	ID   string `json:"id"`
	Name string `json:"name"`
}

var users = []User{
	{ID: "1", Name: "John Doe"},
	{ID: "2", Name: "Jane Doe"},
}

func getUsers(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(users)
}

func getUser(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for _, item := range users {
		if item.ID == params["id"] {
			json.NewEncoder(w).Encode(item)
			return
		}
	}
	http.NotFound(w, r)
}

func createUser(w http.ResponseWriter, r *http.Request) {
	var user User
	_ = json.NewDecoder(r.Body).Decode(&user)
	user.ID = "3" // In a real application, you'd generate a unique ID
	users = append(users, user)
	json.NewEncoder(w).Encode(user)
}

func updateUser(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for index, item := range users {
		if item.ID == params["id"] {
			users = append(users[:index], users[index+1:]...)
			var user User
			_ = json.NewDecoder(r.Body).Decode(&user)
			user.ID = params["id"]
			users = append(users, user)
			json.NewEncoder(w).Encode(user)
			return
		}
	}
	http.NotFound(w, r)
}

func deleteUser(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for index, item := range users {
		if item.ID == params["id"] {
			users = append(users[:index], users[index+1:]...)
			break
		}
	}
	json.NewEncoder(w).Encode(users)
}
