package Commands;

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

        if(!(TimeThread.getNumericalTime('b').equals("10") || TimeThread.getNumericalTime('b').equals(15))){
            e.reply("It's still not allowed to take a break!");
            return;
        }

        io.writeActivity(TimeThread.getDate(), TimeThread.getTime(), io.findUser(ID), "Went out", "reason: " + message[1]);
        io.updateEligibility(ID, 'B');
        e.reply(io.findUser(ID) + " took a break: " + TimeThread.getDate() + " - " + TimeThread.getTime() + " for a reason: " + message[1] );

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
        },1000, 1000);
    }


}
