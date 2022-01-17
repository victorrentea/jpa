package victor.training.jpa.perf.helper;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.currentTimeMillis;

public class PerformanceUtil {


   public static void printUsedHeap(String label) {
      System.out.println(label + ": " + getUsedHeap());
   }

   public static String getUsedHeap() {
      System.gc();
      return "Used heap: " + formatSize(getUsedHeapBytes()).replace(",", " ");
   }

   public static String formatSize(long usedHeapBytes) {
      return String.format("%,d B", usedHeapBytes);
   }

   public static long getUsedHeapBytes() {
      return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
   }

}
