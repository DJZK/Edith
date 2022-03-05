package Event;

import Functions.DatabaseParameters;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Watcher extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Ignores if bot to prevent deleting its own message
        if(event.getAuthor().isBot()){
            return;
        }

        // Deletes the accepted command
        if(event.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
            event.getMessage().delete().queue();
        }

    }
}
