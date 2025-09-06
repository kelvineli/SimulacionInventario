import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger inventario= new AtomicInteger(10);
        AtomicInteger entregastotales= new AtomicInteger(0);
        AtomicInteger ventasTotales= new AtomicInteger(0);

        int operacionesPorHilo= 100;


        Runnable productor = () -> {
            for (int i= 0; i< operacionesPorHilo; i++) {
                inventario.incrementAndGet();
                entregastotales.incrementAndGet();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };


        Runnable consumidor= () -> {
            for (int i= 0; i< operacionesPorHilo; i++) {
                if (inventario.get()> 0) {
                    inventario.decrementAndGet();
                    ventasTotales.incrementAndGet();
                } else {
                    // no hay stock
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread p1= new Thread(productor, "Productor-1");
        Thread p2= new Thread(productor, "Productor-2");
        Thread c1= new Thread(consumidor, "Consumidor-1");
        Thread c2= new Thread(consumidor, "Consumidor-2");

        int inventarioInicial= inventario.get();

        p1.start();
        p2.start();
        c1.start();
        c2.start();

        p1.join();
        p2.join();
        c1.join();
        c2.join();

        System.out.println("Inventario inicial: "+ inventarioInicial);
        System.out.println("Total de entregas realizadas: "+ entregastotales.get());
        System.out.println("Total de ventas realizadas: "+ ventasTotales.get());
        System.out.println("Inventario final: "+ inventario.get());
        System.out.println("Comprobación (inicial+entregas–ventas): " +
                (inventarioInicial+ entregastotales.get()- ventasTotales.get()));
    }
}
