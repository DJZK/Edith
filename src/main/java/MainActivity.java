import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class MainActivity implements EventListener {
    public static void main(String[] args) throws LoginException, InterruptedException {

        DatabaseHandles db = new DatabaseHandles();

        // Will find the database
        DatabaseParameters.initDB();
        db.initStatus();

        JDA jda = JDABuilder.create(DatabaseParameters.getBotToken(),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_INVITES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_WEBHOOKS)
                .addEventListeners(new MainActivity())
                .build();

        // optionally block until JDA is ready
        jda.awaitReady();
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof MainActivity) {
            System.out.println("API is ready!");
        }
    }
}
