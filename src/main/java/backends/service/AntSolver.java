package backends.service;

import backends.Ant;
import backends.Caches;
import backends.City;
import backends.Config;
import backends.model.AbstractSolver;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AntSolver extends AbstractSolver {
    List<Ant> ants = null;//蚁群
    List<City> cities = null;//城市信息记录
    double[][] distance;//城市之间距离矩阵
    double[][] t;//城市之间信息素矩阵
    double c = 5;//初始信息素浓
    double Q = 100;//每次蚂蚁走完所有城市所留下的信素
    double p = 0.90;//信息素保留率
    int index;
    double minDistance;//记录最优解
    List<Integer> shortestPath=Lists.newArrayList();//记录最优解
    Caches cache;
    @Override
    public void process() {
        init();
        for (int i = 0; i < 5000; i++) {
            initAnts();
            antsWalk();
        }
        Config.shortestPath=shortestPath.stream().map(item->item+1).collect(Collectors.toList());
        Config.shortestDistance=calIndividualDistance(Config.shortestPath);
    }

    public void init(){
        cache=new Caches();
        minDistance=Double.MIN_VALUE;
        cities = new ArrayList<>(Config.cities);
        distance = new double[cities.size()+1][cities.size()+1];
        t = new double[cities.size()+1][cities.size()+1];
        for (int i = 0; i < cities.size(); i++) {
            City point = cities.get(i);
            for (int j = 0; j < cities.size(); j++) {
                distance[i][j] = cache.getDistanceByCity(point, cities.get(j));
                t[i][j] = c;
            }
        }
    }
    public void initAnts()//初始化蚁群
    {
        ants = Lists.newArrayList();
        for (int i = 0; i < cities.size(); i++) {
            Ant an = new Ant(i, cities.size());
            ants.add(an);
        }
    }
    public void antsWalk()//单次循环：所有蚂蚁走完所有城市
    {
        for (int i = 1; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                this.chooseCity(j);
            }
        }
        for (int j = 0; j < cities.size(); j++) {
            Ant ant = ants.get(j);
            ant.setDistance(ant.getDistance() + this.getDistancesByCityNo(ant.getCityNo(), ant.getDepartCityNo()));
            if (minDistance > ant.getDistance()) {
                minDistance = ant.getDistance();
                index = j;
                shortestPath = new ArrayList<>(ant.getWalkOverCities());
            }

            ant.getWalkOverCities().add(ant.getDepartCityNo());
            for (int i = 0; i < cities.size(); i++) {
                t[ant.getWalkOverCities().get(i)][ant.getWalkOverCities().get(i + 1)] = t[ant.getWalkOverCities().get(i)][ant.getWalkOverCities().get(i + 1)] * p + Q / ant.getDistance();
                t[ant.getWalkOverCities().get(i + 1)][ant.getWalkOverCities().get(i)] = t[ant.getWalkOverCities().get(i + 1)][ant.getWalkOverCities().get(i)] * p + Q / ant.getDistance();
            }

        }
    }
    //选择下一城市
    public void chooseCity(int n) {
        Ant a = ants.get(n);
        double random = Math.random();
        double sump = 0, sum = 0;
        double p[] = new double[150];
        for (int i = 0; i < a.getCityNumLeft(); i++) {
            double di = 1 / distance[a.getCityNo()][a.getToGoCities().get(i)];//启发因子
            double ti = t[a.getCityNo()][a.getToGoCities().get(i)];//信息素因子
            p[i] = ti * di * di * di * di * di;
            sump += p[i];
        }
        for (int i = 0; i < a.getCityNumLeft(); i++) {
            sum += (p[i] / sump);
            if (random <= sum) {
                a.setDistance(a.getDistance() + this.getDistancesByCityNo(a.getCityNo(), a.getToGoCities().get(i)));
                a.setCityNo(a.getToGoCities().get(i));
                a.setWalkOverCityNum(a.getWalkOverCityNum() + 1);
                a.getWalkOverCities().add(a.getCityNo());
                a.getToGoCities().remove(a.getCityNo());
                a.setCityNumLeft(a.getCityNumLeft() - 1);
                break;
            }
        }
    }

    public double getDistancesByCityNo(int m, int n) {
        City city = cache.getCityByNo(m+1);
        City nextCity = cache.getCityByNo(n+1);
        return Math.sqrt(cache.getDistanceByCity(city, nextCity));
    }

    public double calIndividualDistance(List<Integer> li) {
        double distance = 0;
        if(li==null||li.isEmpty())
            return distance;
        for (int i = 0; i < li.size() - 1; i++) {
            City city = cache.getCityByNo(li.get(i));
            City nextCity = cache.getCityByNo(li.get(i + 1));
            distance += Math.sqrt(cache.getDistanceByCity(city, nextCity));
        }
        City start = cache.getCityByNo(li.get(0));
        City end = cache.getCityByNo(li.get(li.size() - 1));
        distance += Math.sqrt(cache.getDistanceByCity(start, end));
        return distance;
    }
}
