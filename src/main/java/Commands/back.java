package Commands;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class back extends Command {
    public back(){
        this.name = "back";
        this.help = "logs you out!";
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

        io.writeActivity(TimeThread.getDate(), TimeThread.getDate(), io.findUser(e.getAuthor().getId()), "Re-entry", "");
        e.reply(io.findUser(e.getAuthor().getId()) + " got back from break: " + TimeThread.getDate() + " - " + TimeThread.getTime());
    }
}
