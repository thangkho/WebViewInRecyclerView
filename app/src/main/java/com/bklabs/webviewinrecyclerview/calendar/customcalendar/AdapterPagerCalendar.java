//package com.bklabs.webviewinrecyclerview.calendar.customcalendar;
//
//import java.util.ArrayList;
//
//import androidx.fragment.app.FragmentPagerAdapter;
//
//public class AdapterPagerCalendar extends FragmentPagerAdapter {
//    private static final int FIRST_PAGE_POSITION = 0;
//    private List<Date> mListDate;
//
//
//    public AdapterPagerCalendar(FragmentManager fragmentManager, List<Date> list) {
//        super(fragmentManager);
//        this.mListDate = list;
//    }
//
//    @Override
//    public int getCount() {
//        return mListDate.isEmpty() ? 0 : mListDate.size();
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return FragmentMonthView.newInstance(mListDate.get(position));
//    }
//
//
//    public int getLastPagePosition() {
//        return mListDate.isEmpty() && mListDate.size() > 0 ? 0 : mListDate.size() - 1;
//    }
//
//
//    public int getFistPagePosition() {
//        return FIRST_PAGE_POSITION;
//    }
//    public static List<Date> createListCalendar() {
//        List<Date> list = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.MONTH, -PAGE_OF_CURRENT_DAY);
//        while (list.size() < TOTAL_MONTH_SHOW) {
//            list.add(calendar.getTime());
//            calendar.add(Calendar.MONTH, 1);
//        }
//        return list;
//    }
//}