package Commands.Employees;

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


        // Still logged on
        if(io.checkActionEligibility(ID)[0]){
            e.reply("You're still logged on " + e.getAuthor().getAsMention());
            return;
        }

        // Not allowed to log in outside work hours
        if (!(TimeThread.getNumericalTime('b').equals("08") || TimeThread.getNumericalTime('b').equals("13"))) {
            e.reply("Not allowed to log in at this point. What are you doing?");
            System.out.println(TimeThread.getNumericalTime('b'));
            return;
        }


        // Not allowed to log in after 1:30 PM
        if(TimeThread.getNumericalTime('b').equals("13")){
            if (Integer.parseInt(TimeThread.getNumericalTime('a')) > 1330) {
                e.reply("~~all the time but this is, 30 freaking minutes too late.... but i wish you enjoyed your lunch!");
                return;
            }
        }

        // Not allowed to log in after 8:30 AM
        if(TimeThread.getNumericalTime('b').equals("08")){
            if (Integer.parseInt(TimeThread.getNumericalTime('a')) > 830) {
                e.reply("more than 30 minutes late in the morning? Seriously!?");
                return;
            }
        }

        // Actions
        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Logged in", "");
        io.updateEligibility(ID, 'A');
        e.reply(io.findUser(ID) + " logged in: " + TimeThread.getDate() + " - " + TimeThread.getTime());
    }
}
