/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.connectors.dis.internals;

import com.huaweicloud.dis.adapter.kafka.clients.consumer.Consumer;
import com.huaweicloud.dis.adapter.kafka.clients.consumer.DISKafkaConsumer;
import com.huaweicloud.dis.adapter.kafka.common.TopicPartition;
import org.apache.flink.annotation.Internal;

import java.util.Collections;
import java.util.List;

/**
 * The ConsumerCallBridge simply calls the {@link DISKafkaConsumer#assign(java.util.Collection)} method.
 *
 * <p>This indirection is necessary, because Kafka broke binary compatibility between 0.9 and 0.10,
 * changing {@code assign(List)} to {@code assign(Collection)}.
 *
 * <p>Because of that, we need two versions whose compiled code goes against different method signatures.
 */
@Internal
public class DisConsumerCallBridge
{

	public void assignPartitions(Consumer<?, ?> consumer, List<TopicPartition> topicPartitions) throws Exception {
		consumer.assign(topicPartitions);
	}

	public void seekPartitionToBeginning(Consumer<?, ?> consumer, TopicPartition partition) {
		consumer.seekToBeginning(Collections.singletonList(partition));
	}

	public void seekPartitionToEnd(Consumer<?, ?> consumer, TopicPartition partition) {
		consumer.seekToEnd(Collections.singletonList(partition));
	}

    public void pause(Consumer<?, ?> consumer, List<TopicPartition> topicPartitions) {
        consumer.pause(topicPartitions);
    }
}
