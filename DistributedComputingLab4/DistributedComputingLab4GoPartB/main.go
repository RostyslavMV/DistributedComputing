package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"sync"
	"time"
)

var operationsLimit = 20

func gardener(garden [][]int, rwMutex *sync.RWMutex, wg *sync.WaitGroup) {
	for i := 0; i < operationsLimit; i++ {
		rwMutex.Lock()
		for i := 0; i < len(garden); i++ {
			for j := 0; j < len(garden[0]); j++ {
				if garden[i][j] < 2 {
					garden[i][j]++
				}
			}
		}
		rwMutex.Unlock()
	}
	defer wg.Done()
}

func nature(garden [][]int, rwMutex *sync.RWMutex, wg *sync.WaitGroup) {
	for i := 0; i < operationsLimit; i++ {
		rwMutex.Lock()
		for i := 0; i < len(garden); i++ {
			for j := 0; j < len(garden[0]); j++ {
				if garden[i][j] == 2 {
					garden[i][j] -= rand.Intn(3)
				}
				if garden[i][j] == 1 {
					garden[i][j] -= rand.Intn(2)
				}
				if garden[i][j] == 0 {
					garden[i][j] += rand.Intn(3)
				}
			}
		}
		rwMutex.Unlock()
	}
	defer wg.Done()
}

func monitor1(garden [][]int, rwMutex *sync.RWMutex, wg *sync.WaitGroup) {
	file, err := os.Create("output.txt")

	if err != nil {
		fmt.Println("Error occurred during file creation: ", err)
		os.Exit(1)
	}
	defer file.Close()

	for i := 0; i < operationsLimit; i++ {
		rwMutex.RLock()
		for i := 0; i < len(garden); i++ {
			var line = ""
			for j := 0; j < len(garden[0]); j++ {
				line += strconv.Itoa(garden[i][j]) + " "
			}

			_, err := file.WriteString(line + "\n")
			if err != nil {
				panic(err)
			}
		}
		rwMutex.RUnlock()
		_, err := file.WriteString("\n-------------------------------\n\n")
		if err != nil {
			panic(err)
		}
	}
	defer wg.Done()
}

func monitor2(garden [][]int, rwMutex *sync.RWMutex, wg *sync.WaitGroup) {
	for i := 0; i < operationsLimit; i++ {
		rwMutex.RLock()
		for i := 0; i < len(garden); i++ {
			var line = ""
			for j := 0; j < len(garden[0]); j++ {
				line += strconv.Itoa(garden[i][j]) + " "
			}
			fmt.Println(line)
		}
		rwMutex.RUnlock()
		fmt.Println()
	}
	defer wg.Done()
}

func createGarden() [][]int {
	var garden [][]int
	rand.Seed(time.Now().UnixNano())
	for i := 0; i < 10; i++ {
		var gardenRow []int
		for j := 0; j < 10; j++ {
			gardenRow = append(gardenRow, rand.Intn(3))
		}
		garden = append(garden, gardenRow)
	}
	return garden
}

func main() {
	// 0 - bad conditions for plants, 1 - normal, 2 - good
	var garden [][]int = createGarden()

	var rwMutex sync.RWMutex
	var wg sync.WaitGroup
	wg.Add(4)

	go gardener(garden, &rwMutex, &wg)
	go nature(garden, &rwMutex, &wg)
	go monitor1(garden, &rwMutex, &wg)
	go monitor2(garden, &rwMutex, &wg)

	wg.Wait()

	fmt.Scanln()
}
