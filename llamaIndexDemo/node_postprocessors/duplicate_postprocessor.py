from llama_index.core import QueryBundle
from llama_index.core.postprocessor.types import BaseNodePostprocessor
from llama_index.core.schema import NodeWithScore
from typing import List, Optional


class DuplicateRemoverNodePostprocessor(BaseNodePostprocessor):
    def _postprocess_nodes(
        self, nodes: List[NodeWithScore], query_bundle: Optional[QueryBundle]
    ) -> List[NodeWithScore]:
        print("_ My postprocess_nodes enter")

        unique_hashes = set()
        unique_nodes = []

        for node in nodes:
            node_hash = node.node.hash
            if node_hash not in unique_hashes:
                unique_hashes.add(node_hash)
                unique_nodes.append(node)

        return unique_nodes