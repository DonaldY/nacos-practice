package com.donald.nacosconsumer;

import com.donald.nacosconsumer.circuitbreaker.CircuitBreaker;
import com.donald.nacosconsumer.circuitbreaker.Config;
import com.donald.nacosconsumer.circuitbreaker.DegradeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * @author donald
 * @date 2021/01/27
 */
public class CircuitBreakerTest {

    private CircuitBreaker circuitBreaker;

    @Before
    public void setUp() {
        circuitBreaker = new CircuitBreaker(new Config());
    }

    @Test
    public void testScene1() {
        CircuitBreaker cb = new CircuitBreaker(new Config());
        String bookName = cb.run(() -> "deep in spring cloud", t -> {
            System.err.println(t);
            return "boom";
        });
        Assert.assertEquals("deep in spring cloud", bookName);
    }

    @Test
    public void testScene2() {
        CircuitBreaker cb = new CircuitBreaker(new Config());
        RestTemplate restTemplate = new RestTemplate();
        String result = cb.run(() -> {
            return restTemplate.getForObject("https://httpbin.org/status/500", String.class);
        }, t -> {
            System.err.println(t);
            return "boom";
        });
        System.out.println(result);
    }

    @Test
    public void testScene3() {
        Config config = new Config();
        config.setFailureCount(2);
        CircuitBreaker cb = new CircuitBreaker(config);
        RestTemplate restTemplate = new RestTemplate();

        int degradeCount = 0;

        for(int index = 0; index < 10; index ++) {
            String result = cb.run(() -> {
                return restTemplate.getForObject("https://httpbin.org/status/500", String.class);
            }, t -> {
                if(t instanceof DegradeException) {
                    return "degrade";
                }
                return "boom";
            });
            if(result.equals("degrade")) {
                degradeCount ++;
            }
        }

        System.out.println(degradeCount);
    }

    @Test
    public void testClosedStatus() {
        // make sure in closed status
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            // ignore
        }
        String bookName = circuitBreaker.run(() -> {
            return "deep in spring cloud";
        }, t -> {
            System.err.println(t);
            return "boom";
        });
        Assert.assertEquals("deep in spring cloud", bookName);
    }

    @Test
    public void testOpenStatus() {
        // make sure in closed status
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            // ignore
        }
        for (int index = 1; index <= 10; index++) {
            int finalIndex = index;
            String bookName = circuitBreaker.run(() -> {
                throw new IllegalStateException("Oops");
            }, t -> {
                System.err.println(t);
                if (finalIndex > 5) {
                    Assert.assertTrue(t instanceof DegradeException);
                }
                return null;
            });
            if (bookName != null) {
                System.out.println(finalIndex + "-" + bookName);
            }
        }
    }

    @Test
    public void testHalfOpen2Open() {
        // make sure in closed status
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            // ignore
        }
        for (int index = 1; index <= 10; index++) {
            int finalIndex = index;
            if (finalIndex == 6) {
                try {
                    Thread.sleep(6000l);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            String bookName = circuitBreaker.run(() -> {
                if (finalIndex == 6) {
                    return "deep in spring cloud";
                }
                throw new IllegalStateException("Oops");
            }, t -> {
                System.err.println(t);
                if (finalIndex > 6) {
                    Assert.assertTrue(t instanceof DegradeException);
                }
                return null;
            });
            if (bookName != null) {
                System.out.println(finalIndex + "-" + bookName);
            }
        }
    }

    @Test
    public void testHalfOpen2Closed() {
        // make sure in closed status
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            // ignore
        }
        for (int index = 1; index <= 10; index++) {
            int finalIndex = index;
            if (finalIndex == 6) {
                try {
                    Thread.sleep(6000l);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            String bookName = circuitBreaker.run(() -> {
                if (finalIndex == 6 || finalIndex == 7) {
                    return "deep in spring cloud";
                }
                throw new IllegalStateException("Oops");
            }, t -> {
                System.err.println(t);
                if (finalIndex > 7) {
                    Assert.assertTrue(t.getMessage().equals("Oops"));
                }
                return null;
            });
            if (bookName != null) {
                System.out.println(finalIndex + "-" + bookName);
            }
        }
    }

}
