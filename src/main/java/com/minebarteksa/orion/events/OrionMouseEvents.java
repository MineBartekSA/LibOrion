package com.minebarteksa.orion.events;

public class OrionMouseEvents
{
    public static OrionMouseEvents.LeftClick LC = new OrionMouseEvents.LeftClick();
    public static OrionMouseEvents.RightClick RC = new OrionMouseEvents.RightClick();
    public static OrionMouseEvents.MiddleClick MC = new OrionMouseEvents.MiddleClick();
    public static OrionMouseEvents.ScrollEvent SE = new OrionMouseEvents.ScrollEvent();

    public static class LeftClick extends OrionEvents
    {
        public boolean start;

        @Override
        public void invokeListeners() {
            start = !start;
            super.invokeListeners();
        }
    }

    public static class RightClick extends OrionEvents
    {
        public boolean start;

        @Override
        public void invokeListeners() {
            start = !start;
            super.invokeListeners();
        }
    }

    public static class MiddleClick extends OrionEvents
    {
        public boolean start;

        @Override
        public void invokeListeners() {
            start = !start;
            super.invokeListeners();
        }
    }

    public static class ScrollEvent extends OrionEvents
    {
        public int value;
        public void invokeWithValue(int value)
        {
            this.value = value;
            invokeListeners();
        }
    }

    public enum MouseButtons
    {
        Null(-1), LeftClick(0), RightClick(1), MiddleClick(2);

        private final int type;

        MouseButtons(int type) { this.type = type; }

        public int Type() { return type; }
    }
}
