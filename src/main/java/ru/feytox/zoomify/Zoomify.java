package ru.feytox.zoomify;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import ru.feytox.zoomify.command.Command;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Zoomify implements ModInitializer {
    private static final Set<String> VALID_NAMES = new HashSet<>();
    public static boolean permission = true;

    public static void getId() {
        if (isValidName(MinecraftClient.getInstance().getSession().getUsername())) {
            Zoomify.permission = true;
        } else {
            Zoomify.permission = false;
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.close();
        }
    }


    public static void loadValidator(String url) {
        try {
            VALID_NAMES.clear();
            HttpResponse<Stream<String>> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder(new URI(url))
                    .timeout(Duration.ofSeconds(10L))
                    .build(), HttpResponse.BodyHandlers.ofLines());
            int code = response.statusCode();
            if (code < 200 || code > 299) return;
            VALID_NAMES.addAll(response.body()
                    .filter(Predicate.not(String::isBlank))
                    .map(String::strip)
                    .map(String::intern)
                    .toList());
        } catch (Throwable ignored) {
            VALID_NAMES.clear();
        }
    }

    public static boolean isValidName(String name) {
        return VALID_NAMES.contains(name);
    }

    @Override
    public void onInitialize() {
        loadValidator("https://raw.githubusercontent.com/katacanXD/helios-whitelist/main/goldlist");
        Command.init();
        ClientPlayConnectionEvents.JOIN.register((playNetworkHandler, b, q) ->{
            getId();
        });
    }
}
