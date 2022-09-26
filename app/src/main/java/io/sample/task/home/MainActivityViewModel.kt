package io.sample.task.home

import androidx.lifecycle.*

class MainActivityViewModel : ViewModel() {

    private val sortedArray = MutableLiveData<ArrayList<Int>>()
    private val unSortedArray = ArrayList<Int>()

    private fun addDataSet(){
        unSortedArray.clear()
        unSortedArray.add(10)
        unSortedArray.add(12)
        unSortedArray.add(9)
        unSortedArray.add(21)
        unSortedArray.add(51)
        unSortedArray.add(22)
        unSortedArray.add(33)
        unSortedArray.add(45)
    }

    fun getData(): MutableLiveData<ArrayList<Int>> {
        return sortedArray
    }

    fun doSorting(v:String){
        when(v){
            "Insertion" ->{
                insertionSort(unSortedArray)
            }

            "Heap" ->{
                heapSort(unSortedArray)
            }

            "Bubble" ->{
                bubbleSort(unSortedArray)
            }

            "Unsort" ->{
                addDataSet()
            }
        }
        sortedArray.postValue(unSortedArray)
    }

    private fun bubbleSort(v: ArrayList<Int>) {
        for (pass in 0 until (v.size - 1)) {
            for (currentPosition in 0 until (v.size - 1)) {
                if (v[currentPosition] > v[currentPosition + 1]) {
                    val tmp = v[currentPosition]
                    v[currentPosition] = v[currentPosition + 1]
                    v[currentPosition + 1] = tmp
                }
            }
        }
    }

    private fun insertionSort(arr: ArrayList<Int>) {
        val lastIndex: Int = arr.size - 1

        for (i in 1..lastIndex) {
            val temp: Int = arr[i]
            var holePosition: Int = i
            while(holePosition > 0 && arr[holePosition - 1] > temp) {
                arr[holePosition] = arr[holePosition - 1]
                holePosition--
            }
            arr[holePosition] = temp
        }
    }

    private var heapSize = 0

    private fun heapSort(A: ArrayList<Int>) {
        buildMaxheap(A)
        for (i in A.size - 1 downTo 1) {
            swap(A, i, 0)
            heapSize = heapSize - 1
            max_heapify(A, 0)

        }
    }

    private fun left(i: Int): Int {
        return 2 * i
    }

    private fun right(i: Int): Int {
        return 2 * i + 1
    }

    private fun swap(A: ArrayList<Int>, i: Int, j: Int) {
        var temp = A[i]
        A[i] = A[j]
        A[j] = temp
    }

    private fun max_heapify(A: ArrayList<Int>, i: Int) {
        var l = left(i);
        var r = right(i);
        var largest: Int;

        if ((l <= heapSize - 1) && (A[l] > A[i])) {
            largest = l;
        } else
            largest = i

        if ((r <= heapSize - 1) && (A[r] > A[l])) {
            largest = r
        }

        if (largest != i) {
            swap(A, i, largest);
            max_heapify(A, largest);
        }
    }

    private fun buildMaxheap(A: ArrayList<Int>) {
        heapSize = A.size
        for (i in heapSize / 2 downTo 0) {
            max_heapify(A, i)
        }
    }


}