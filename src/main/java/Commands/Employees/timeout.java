package Commands.Employees;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class timeout extends Command {
    public timeout() {
        this.name = "break";
        this.aliases = new String[]{"timeout", "pee", "coffee"};
        this.help = "Break time!";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e) {
        DatabaseHandles io = new DatabaseHandles();
        String ID = e.getAuthor().getId();

        // Not part of team
        if (io.findUser(e.getAuthor().getId()).equals("")) {
            e.replyInDm("You are not allowed to do that!");
            if (!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())) {
                e.getMessage().delete().queue();
            }
            return;
        }

        // Not in the designated text channel
        if (!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())) {
            e.getMessage().delete().queue();
            e.getJDA().getTextChannelById(DatabaseParameters.getChannelID()).sendMessage("Do that here! " + e.getAuthor().getAsMention()).queue();
            return;
        }

        // Not Logged On
        if (!io.checkActionEligibility(ID)[0]) {
            e.reply("You're not even logged on lol " + e.getAuthor().getAsMention());
            return;
        }

        // already on break
        if (io.checkActionEligibility(ID)[1]) {
            e.reply("You're already on break and you wanna take another one? What are you planning to do " + e.getAuthor().getAsMention() + "?");
            return;
        }

        // Not allowed to take a break outside working hours
        if (!(TimeThread.getNumericalTime('b').equals("10") || TimeThread.getNumericalTime('b').equals("15"))) {
            e.reply("Not allowed to take a break at this hour");
            return;
        }


        // Not allowed up to 3:15 PM
        if(TimeThread.getNumericalTime('b').equals("15")){
            if (Integer.parseInt(TimeThread.getNumericalTime('a')) > 1515) {
                e.reply("Too late my guy.. too late..");
                return;
            }
        }


        // Not allowed up to 10:15 AM
        if(TimeThread.getNumericalTime('b').equals("10")){
            if (Integer.parseInt(TimeThread.getNumericalTime('a')) > 1015) {
                e.reply("Too late my guy.. too late..");
                return;
            }
        }



        // Will Take A break
        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Went out", "");
        io.updateEligibility(ID, 'B');
        e.reply(io.findUser(ID) + " took a break: " + TimeThread.getDate() + " - " + TimeThread.getTime());
        e.reply("Your 15 minutes starts.... now!! " + e.getAuthor().getAsMention());


        // Expires in 15minutes
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                e.reply("Time's up! " + e.getAuthor().getAsMention());
                io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Got back from break!", "");
                io.updateEligibility(ID, 'B');

                t.cancel();
                t.purge();
            }
        }, 900000, 900000);
    }


}
