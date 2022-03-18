package Commands;

import Functions.DatabaseParameters;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(e.getAuthor().isBot()){
            return;
        }

        if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getConsoleChannel())){
            return;
        }

        if(!e.getMessage().getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
            return;
        }

        String[] cmd = {"/bin/bash","-c","echo "+ DatabaseParameters.getSudoPass() + "| sudo -S " + e.getMessage().getContentRaw()};
        Process pb = null;
        try {
            pb = Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String line = "";
        BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.getJDA().getGuildById(e.getMessage().getGuild().getId()).getTextChannelById(DatabaseParameters.getConsoleChannel()).sendMessage(line + "").queue();
            System.out.println(line);
        }
        try {
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
