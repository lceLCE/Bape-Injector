package Client;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import you.cant.fucking.crack.it.verify;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
    public static String path = System.getProperty("user.dir");

    public static void main(String[] args) throws IOException {
        //verify.doVerify();
        File dll = new File(path + "\\attach.dll");
        File clientJar = new File(path + "\\client.jar");

        System.loadLibrary("attach");
        VirtualMachineDescriptor vm = VirtualMachine.list().stream().filter(m -> m.displayName().startsWith("net.minecraft.launchwrapper.Launch")).findFirst().orElse(null);
        if (vm == null) {
            JOptionPane.showConfirmDialog(null,"No Minecraft Process Found.","ERROR",
                    JOptionPane.YES_NO_OPTION);

            error("No Minecraft Process Found.");
            return;
        }

        if (!dll.exists()) {
            JOptionPane.showConfirmDialog(null,"No attach.dll found.","ERROR",
                    JOptionPane.YES_NO_OPTION);
            error("No attach.dll found.");
            return;
        }

        try {
            VirtualMachine attach = VirtualMachine.attach(vm);
            if (!clientJar.exists()) {
                JOptionPane.showConfirmDialog(null,"Client broken","ERROR",
                        JOptionPane.YES_NO_OPTION);
                error("No Client.jar Found.");
                return;
            }
            try {
                attach.loadAgent(clientJar.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            attach.detach();
            JOptionPane.showConfirmDialog(null,"Client load successful","Load successful",
                    JOptionPane.YES_NO_OPTION);
            error("Press Rshift to open the ClickGUI");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void error(String message) throws IOException {
        System.out.print(message);
    }
}