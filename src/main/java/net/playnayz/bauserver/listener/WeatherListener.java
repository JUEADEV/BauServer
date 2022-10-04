package net.playnayz.bauserver.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void handleWorldWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
