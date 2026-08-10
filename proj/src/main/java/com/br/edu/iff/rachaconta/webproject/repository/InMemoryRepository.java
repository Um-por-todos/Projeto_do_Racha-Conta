package com.br.edu.iff.rachaconta.webproject.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class InMemoryRepository<T> {
    private final Map<Long,T> data = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);
    private final Function<T,Long> idGetter;
    private final java.util.function.BiConsumer<T,Long> idSetter;
    public InMemoryRepository(Function<T,Long> idGetter, java.util.function.BiConsumer<T,Long> idSetter){this.idGetter=idGetter;this.idSetter=idSetter;}
    public synchronized T save(T entity){Long id=idGetter.apply(entity); if(id==null){id=sequence.incrementAndGet();idSetter.accept(entity,id);} else { final long currentId=id; sequence.updateAndGet(x->Math.max(x,currentId)); } data.put(id,entity); return entity;}
    public Optional<T> findById(Long id){return Optional.ofNullable(data.get(id));}
    public List<T> findAll(){return new ArrayList<>(data.values());}
    public boolean existsById(Long id){return data.containsKey(id);}
    public void deleteById(Long id){data.remove(id);}
    public void deleteAll(){data.clear();sequence.set(0);}
}
