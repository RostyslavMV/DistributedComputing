package main

import (
	"fmt"
)

var goodsCount int = 5

func ivanovTake(takenGoodsChan chan int) {
	for i := 0; i < goodsCount; i++ {
		fmt.Println("Ivanov took a new good from military base.")
		takenGoodsChan <- 1
	}
}

func petrovLoad(takenGoodsChan chan int, loadedGoodsChan chan int) {
	for i := 0; i < goodsCount; i++  {
		<-takenGoodsChan
		fmt.Println("Petrov loaded a new good into a truck.")
		loadedGoodsChan <- 1
	}
}

func nechiporchukCountPrice(loadedGoodsChan chan int) {
	for i := 0; i < goodsCount; i++ {
		<-loadedGoodsChan
		fmt.Println("Nechiporchuk added a new good price to the total price.")
	}
}

func main() {
	var takenGoodsChan = make(chan int, 1)
	var loadedGoodsChan = make(chan int, 1)

	go ivanovTake(takenGoodsChan)
	go petrovLoad(takenGoodsChan, loadedGoodsChan)
	go nechiporchukCountPrice(loadedGoodsChan)

	fmt.Scanln()
}
