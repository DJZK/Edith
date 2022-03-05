package Commands;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class visitor extends Command {
    public visitor(){
        this.name = "visitor";
        this.aliases = new String []{"visit", "log"};
        this.help = "visitor and their activity!";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e){
        DatabaseHandles io = new DatabaseHandles();
        if(io.findUser(e.getAuthor().getId()).equals("")){
            e.replyInDm("You are not allowed to do that!");
            if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
                e.getMessage().delete().queue();
            }
            return;
        }

        if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
            e.getMessage().delete().queue();
            e.getJDA().getTextChannelById(DatabaseParameters.getChannelID()).sendMessage("Do that here! " + e.getAuthor().getAsMention()).queue();
            return;
        }

        String[] message = e.getMessage().getContentRaw().split(" ", 2);

        // Single command input
        if(message.length == 1){
            e.reply(DatabaseParameters.getBotPrefix() + "visitor <who, and their activity>");
            return;
        }

        io.writeActivity(TimeThread.getDate(), TimeThread.getDate(), io.findUser(e.getAuthor().getId()), "Visitor",  message[1]);
        e.reply(io.findUser(e.getAuthor().getId()) + " recorded a visitor at: " + TimeThread.getDate() + " - " + TimeThread.getTime() + "\ndetails: " + message[1] );
    }
}
