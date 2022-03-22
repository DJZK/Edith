package Commands.Employees;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class overwrite extends Command {
    public overwrite(){
        this.name = "overwrite";
        this.aliases = new String[] {"ow", "override", "insert" ,"or"};
        this.userPermissions = new Permission[] {Permission.ADMINISTRATOR};
        this.help = "Will insert values in database";
        this.hidden = false;
        this.guildOnly = true;
    }

    @Override
    protected void execute (CommandEvent e){

        // Not int the designated place
        if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
            e.getMessage().delete().queue();
            e.getJDA().getTextChannelById(DatabaseParameters.getChannelID()).sendMessage("Do that here! " + e.getAuthor().getAsMention()).queue();
            return;
        }

        String[] message = e.getMessage().getContentRaw().split(", ", 4);

        // Not longer than 3 parameters
        if(message.length < 3){
            e.reply(DatabaseParameters.getBotPrefix() + "overwrite , <Name> , <Activity> , <Reason>");
            return;
        }

        // Actions
        DatabaseHandles io = new DatabaseHandles();

        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), message[1], message[2],  message[3]);
        e.reply("Override! " + e.getAuthor().getAsMention() + " recorded:" +
                "\nName: " + message[1] + " " +
                "\nActivity: " + message[2] +" " +
                "\nReason: " + message[3] + " " +
                "\nDate and Time: " + TimeThread.getDate() + " " + TimeThread.getTime());
    }
}
