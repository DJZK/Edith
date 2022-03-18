package Commands;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class back extends Command {
    public back(){
        this.name = "back";
        this.aliases = new String []{"reentry", "relog"};
        this.help = "logs you back in from break!";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e){
        DatabaseHandles io = new DatabaseHandles();
        String ID = e.getAuthor().getId();

        // Not part of team
        if(io.findUser(ID).equals("")){
            e.replyInDm("You are not allowed to do that!");
            if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
                e.getMessage().delete().queue();
            }
            return;
        }

        // Not in the designated channel
        if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
            e.getMessage().delete().queue();
            e.getJDA().getTextChannelById(DatabaseParameters.getChannelID()).sendMessage("Do that here! " + e.getAuthor().getAsMention()).queue();
            return;
        }

        // Not Logged On
        if(!io.actionEligibility(ID)[0]){
            e.reply("You haven't even logged on lol " + e.getAuthor().getAsMention());
            return;
        }

        // Not on Break
        if(!io.actionEligibility(ID)[1]){
            e.reply("You're not on break " + e.getAuthor().getAsMention());
            return;
        }

        // Actions
        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Got back from break!", "");
        io.updateEligibility(ID, 'B');
        e.reply(io.findUser(ID) + " got back from break: " + TimeThread.getDate() + " - " + TimeThread.getTime());
    }
}
