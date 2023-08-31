public class Clock implements Runnable {
    private Window window;
    Clock(Window w){
        window = w;
    }

    String formatNumber(int number){
        var stringValue = String.valueOf(number);
        return stringValue.length() == 1 ? "0"+number : stringValue;
    }

    @Override
    public void run() {
        var minutes = 0;
        var seconds = 0;
        String minutesString = "";
        String secondsString = "";
        for (minutes = 0; minutes < 2; minutes++) {
             minutesString =  formatNumber(minutes);
            for (seconds = 0; seconds < 60; seconds++) {
                 secondsString = formatNumber(seconds);
                var timeString = "00:"+minutesString + ":" + secondsString;
                window.updateTimeString(timeString);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        window.updateTimeString("00:02:00");
   }
}
