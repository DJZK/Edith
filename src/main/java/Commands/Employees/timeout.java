package Commands.Employees;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import Functions.TimeThread;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Timer;
import java.util.TimerTask;

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
        String ID = e.getAuthor().getId();

        // Not part of team
        if(io.findUser(e.getAuthor().getId()).equals("")){
            e.replyInDm("You are not allowed to do that!");
            if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
                e.getMessage().delete().queue();
            }
            return;
        }

        // Not in the designated text channel
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

        // Not Logged On
        if(!io.actionEligibility(ID)[0]){
            e.reply("You're not even logged on lol " + e.getAuthor().getAsMention());
            return;
        }

        // already on break
        if(io.actionEligibility(ID)[1]){
            e.reply("You're already on break and you wanna take another one? What are you planning to do " + e.getAuthor().getAsMention() + "?");
            return;
        }

        // Not allowed up to 10:30 AM
        if(TimeThread.getNumericalTime('b').equals("10")){
            if(Integer.parseInt(TimeThread.getNumericalTime('a')) > 1015) {
                e.reply("Too late my guy.. too late..");
                return;
            }
        }

        // Not allowed up to 3:15 PM
        if(TimeThread.getNumericalTime('b').equals("15")){
            if(Integer.parseInt(TimeThread.getNumericalTime('a')) > 1515) {
                e.reply("Too late my guy.. too late..");
                return;
            }
        }

        // Not allowed 15mins before 12 PM
        if(TimeThread.getNumericalTime('b').equals("11")){
            if(Integer.parseInt(TimeThread.getNumericalTime('b')) > 1145){
                e.reply("It's not allowed to take a break before lunch. Extending time eh? hahaha sneaky");
            }
            return;
        }

        // Not allowed 15mins before 5 PM
        if(TimeThread.getNumericalTime('b').equals("16")){
            if(Integer.parseInt(TimeThread.getNumericalTime('b')) > 1645){
                e.reply("Nope. You're not gonna out early.. nope");
            }
            return;
        }


        // Will Take A break
        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Went out", "reason: " + message[1]);
        io.updateEligibility(ID, 'B');
        e.reply(io.findUser(ID) + " took a break: " + TimeThread.getDate() + " - " + TimeThread.getTime() + " for a reason: " + message[1] );


        // Expires in 15minutes
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                e.reply("Time out "+ e.getAuthor().getAsMention());
                io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Got back from break!", "");
                io.updateEligibility(ID, 'B');

                t.cancel();
                t.purge();
            }
        },900000, 900000);
    }


}
