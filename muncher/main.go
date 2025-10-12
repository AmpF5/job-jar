package main

import (
	"encoding/json"
	"fmt"

	"github.com/streadway/amqp"
)

func main() {
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	if err != nil {
		fmt.Println(err)
		panic(err)
	}

	defer conn.Close()

	ch, err := conn.Channel()
	if err != nil {
		fmt.Println(err)
		panic(err)
	}

	defer ch.Close()

	data, err := ch.Consume(
		"offers.collected",
		"",
		true,
		false,
		false,
		false,
		nil,
	)

	forever := make(chan bool)

	go func() {
		for d := range data {
			var offerCreate OfferCreate
			print(d.Body)
			err := json.Unmarshal(d.Body, &offerCreate)
			if err != nil {
				fmt.Println(err)
			}

			fmt.Println(offerCreate)
		}
	}()

	<-forever
}
