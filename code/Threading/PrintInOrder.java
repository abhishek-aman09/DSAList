package Threading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class PrintInOrder {
    
    static class ZeroEvenOdd {
        private int n;
        private boolean isZeroPrint = false;
        private int num = 1;
        
        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {

            synchronized (this) {
                while (num <= n) {
                    while(isZeroPrint) {
                        wait();
                    }
                    if (num <= n) {
                        printNumber.accept(0);
                    }
                    isZeroPrint = true;
                    notifyAll();
                }
                
            }
            
        }

        public void even(IntConsumer printNumber) throws InterruptedException {

            synchronized (this) {
                while (num <= n) {
                    while (!isZeroPrint || num % 2 != 0) {
                        wait();
                    }
                    if (num <= n) {
                        printNumber.accept(num);
                    }
                    isZeroPrint = false;
                    num++;
                    notifyAll();
                }
                
            }
            
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {

            synchronized (this) {
                while (num <= n) {
                        while(!isZeroPrint || num % 2 == 0) {
                        wait();
                    }
                    if (num <= n) {
                        printNumber.accept(num);
                    }
                    isZeroPrint = false;
                    num++;
                    notifyAll();
                }
                
            }

            
        }
    }
    static class FizzBuzz {
        private int n;
        private int num = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            synchronized (this) {
                while (true) {
                    while(num <= n && (num % 3 != 0 || num % 5 == 0)) {
                        wait();
                    }
                    
                    if (num > n) {
                        notifyAll();
                        break;
                    }
                    num++;
                    printFizz.run();
                    notifyAll();
                }
                }
                
            
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            synchronized(this) {
                while(true) {
                    while(num <= n &&  (num % 5 != 0 || num % 3 == 0)) {
                        wait();
                    }
                    
                    if (num > n) {
                        notifyAll();
                        break;
                    }
                    num++;
                    printBuzz.run();

                    notifyAll();
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            synchronized(this) {
                while(true) {
                    while(num <= n && (num % 3 != 0 || num % 5 != 0)) {
                        wait();
                    }
                    
                    if (num > n) {
                        notifyAll();
                        break;
                    }
                    num++;
                    printFizzBuzz.run();

                    notifyAll();
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            synchronized(this) {
                while (true) {
                    while (num <= n && (num % 3 == 0 || num % 5 == 0)) {
                        wait();
                    }
                    if (num > n) {
                        notifyAll();
                        break;
                    }
                    printNumber.accept(num);
                    num++;

                    notifyAll();
                }
            }
        }
    }
   
    public static void main(String[] args) {
        PrintInOrder.ZeroEvenOdd obj = new ZeroEvenOdd(5);

        PrintInOrder.FizzBuzz fizzObj = new FizzBuzz(5);

        IntConsumer printNumber = new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.print(value);
            }
        };

        Runnable zeroTask = new Runnable() {
            @Override
            public void run() {
                try{
                    obj.zero(printNumber);
                } catch (Exception e) {
                    
                }
                
            }
        };

        Runnable oddTask = new Runnable() {
            @Override
            public void run() {
                try{
                    obj.odd(printNumber);
                } catch (Exception e) {
                    
                }
            }
        };

        Runnable evenTask = new Runnable() {
            @Override
            public void run() {
                try{
                    obj.even(printNumber);
                } catch (Exception e) {
                    
                }
                
            }
        };

        Runnable fizzTask = new Runnable() {
            @Override
            public void run() {
                System.out.print(" fizz ");
            }
        };

        Runnable buzzTask = new Runnable() {
            @Override
            public void run() {
                System.out.print(" buzz ");
            }
        };

        Runnable fizzBuzzTask = new Runnable() {
            @Override
            public void run() {
                System.out.print(" fizzbuzz ");
            }
        };
    
        Runnable numTask = new Runnable() {
            @Override
            public void run() {
                try{
                    fizzObj.number(printNumber);
                } catch (Exception e) {

                }
            }
        };
        
        Thread fizz = new Thread(
                () -> {
                    try {
                        fizzObj.fizz(fizzTask);
                    } catch (Exception e) {

                    }
                
            }
        );
        Thread buzz = new Thread(() -> {
                    try {
                        fizzObj.buzz(buzzTask);
                    } catch (Exception e) {
                        
                    }
                
            });
        Thread fizzBuzz = new Thread(() -> {
                    try {
                        fizzObj.fizzbuzz(fizzBuzzTask);
                    } catch (Exception e) {
                        
                    }
                
            });
        Thread number = new Thread(numTask);

        fizz.start();
        buzz.start();
        fizzBuzz.start();
        number.start();
    }

}
