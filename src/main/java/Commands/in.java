package Commands;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class in extends Command {
    public in(){
        this.name = "in";
        this.aliases = new String []{"login", "logon", "signin"};
        this.help = "logs you in!";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e){
        DatabaseHandles io = new DatabaseHandles();
        String ID = e.getAuthor().getId();

        // Not part of team
        if(io.findUser(e.getAuthor().getId()).equals("")){
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

        String[] message = e.getMessage().getContentRaw().split(" ", 2);

        // Single command input
        if(message.length == 1){
            e.reply(DatabaseParameters.getBotPrefix() + "in <site/remote> \nsite = from office \nremote = work from home... or wherever you are.");
            return;
        }

        if(!(message[1].equalsIgnoreCase("site") || message[1].equalsIgnoreCase("remote"))){
            e.reply("From where? Where are you working from...??");
            return;
        }

        if(io.actionEligibility(ID)[0]){
            e.reply("You're still logged on " + e.getAuthor().getAsMention());
            return;
        }

        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Logged in", "from " + message[1]);
        io.updateEligibility(ID, 'A');
        e.reply(io.findUser(ID) + " logged in: " + TimeThread.getDate() + " - " + TimeThread.getTime() + " from " + message[1] );
    }
}
