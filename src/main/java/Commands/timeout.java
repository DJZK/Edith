package Commands;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class timeout extends Command {
    public timeout(){
        this.name = "break";
        this.aliases = new String [] {"timeout", "pee", "coffee"};
        this.help = "Break time!";
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
            e.reply(DatabaseParameters.getBotPrefix() + "break <reason>");
            return;
        }

        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(e.getAuthor().getId()), "Went out", "reason: " + message[1]);
        e.reply(io.findUser(e.getAuthor().getId()) + " took a break: " + TimeThread.getDate() + " - " + TimeThread.getTime() + " for a reason: " + message[1] );
    }


}
