package main

import (
	"fmt"
	"math/rand"
	"time"
)

var componentMap = map[int]string{
	0: "paper",
	1: "tobacco",
	2: "matches",
}

func smoker(name string, componentChan chan int, availableComponent int) {
	for {
		<-componentChan
		fmt.Println(name + " made a cigarette, he had " + componentMap[availableComponent] +
			" and producer gave " + producerComponents(availableComponent))
	}
}

func producerComponents(smokerComponent int) string {
	var result string = ""
	for i := 0; i < len(componentMap); i++ {
		if i != smokerComponent {
			result += componentMap[i]
			if i != len(componentMap)-1 {
				result += ", "
			}
		}
	}
	return result
}

func producer(paperChan chan int, tobaccoChan chan int, matchesChan chan int) {
	for i := 0; i < 5; i++ {
		switch rand.Intn(3) {
		case 0:
			paperChan <- 1
		case 1:
			tobaccoChan <- 1
		case 2:
			matchesChan <- 1
		}
		time.Sleep(time.Millisecond * 1000)
	}
}

func main() {
	var paperChan = make(chan int)
	var tobaccoChan = make(chan int)
	var matchesChan = make(chan int)

	go smoker("Smoker 0", paperChan, 0)
	go smoker("Smoker 1", tobaccoChan, 1)
	go smoker("Smoker 2", matchesChan, 2)
	go producer(paperChan, tobaccoChan, matchesChan)

	fmt.Scanln()
}
