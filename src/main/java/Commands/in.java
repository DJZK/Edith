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
            e.reply(DatabaseParameters.getBotPrefix() + "in <site/remote> \nsite = from office \nremote = work from home... or wherever you are.");
            return;
        }

        if(!(message[1].equalsIgnoreCase("site") || message[1].equalsIgnoreCase("remote"))){
            e.reply("From where? Where are you working from...??");
            return;
        }
        io.writeActivity(TimeThread.getDate(), TimeThread.getDate(), io.findUser(e.getAuthor().getId()), "Logged in", "from " + message[1]);
        e.reply(io.findUser(e.getAuthor().getId()) + " logged in: " + TimeThread.getDate() + " - " + TimeThread.getTime() + " from " + message[1] );
    }
}
