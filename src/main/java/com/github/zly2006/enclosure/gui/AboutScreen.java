package com.github.zly2006.enclosure.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class AboutScreen extends Screen {
    final Screen parent;
    final List<ClickableTextWidget> textWidgets = new ArrayList<>();
    public static final String WIKI_ZH = "https://enclosure.fandom.com/zh/wiki/Enclosure_Wiki";
    public static final String WIKI_EN = "https://enclosure.fandom.com";

    public AboutScreen(Screen parent) {
        super(Text.of("About"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        textWidgets.clear();
        super.init();

        assert client != null;

        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("enclosure.about.author"), null, button -> {
        }, 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("enclosure.about.translator"), null, button -> {
        }, 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("enclosure.about.team_page").formatted(Formatting.UNDERLINE), new TranslatableText("enclosure.about.click_to_open"),
            button -> ConfirmLinkScreen.open("https://www.starlight.cool/", this, true), 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("enclosure.about.copyright"), null, button -> {
        }, 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new LiteralText("Get source code at Github").formatted(Formatting.UNDERLINE), new TranslatableText("enclosure.about.click_to_open"),
            button -> ConfirmLinkScreen.open("https://github.com/zly2006/Enclosure/", this, true), 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("点击查看中文wiki页面").formatted(Formatting.UNDERLINE), new TranslatableText("enclosure.about.click_to_open"),
            button -> ConfirmLinkScreen.open(WIKI_ZH, this, true), 5, 5, width - 20));
        textWidgets.add(new ClickableTextWidget(client, parent, new TranslatableText("Click to open English wiki page").formatted(Formatting.UNDERLINE), new TranslatableText("enclosure.about.click_to_open"),
            button -> ConfirmLinkScreen.open(WIKI_EN, this, true), 5, 5, width - 20));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int centerHeight = height / 2;
        int centerWidth = width / 2;
        int renderStart = Math.max(50, centerHeight - 80);
        drawTextAtCenter(matrices, Text.of("About Enclosure"), centerWidth, 10);
        renderStart += 10;
        renderBackground(matrices);
        for (ClickableTextWidget textWidget : textWidgets) {
            textWidget.x = 10;
            textWidget.y = renderStart;
            textWidget.render(matrices, mouseX, mouseY, delta);
            renderStart += textWidget.getHeight() + 10;
        }
    }

    @Override
    public List<? extends Element> children() {
        List<Element> children = new ArrayList<>(super.children());
        children.addAll(textWidgets);
        return children;
    }

    @Override
    public void close() {
        assert client != null;
        client.setScreen(parent);
    }

    private void drawTextAtCenter(MatrixStack matrices, Text text, int center, int y) {
        // draw text at the center
        textRenderer.draw(matrices, text, center - textRenderer.getWidth(text) / 2.0f, y, 0xffffff);
    }

    private static class ConfirmLinkScreen {
        static void open(String url, AboutScreen aboutScreen, @SuppressWarnings("SameParameterValue") boolean b) {
            ConfirmChatLinkScreen screen = new ConfirmChatLinkScreen(b1 -> MinecraftClient.getInstance().setScreen(aboutScreen), url, b);
        }
    }
}
